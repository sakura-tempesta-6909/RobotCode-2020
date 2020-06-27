<?php
$line=array(
    'accessToken' => 'ypEkWW8dBr9U8gvlpL70XxNRQ7eT6bYWq32LH9AiEym6nAALjQw4J4JPrIx/d/X36Cr43odfxsHJZ1W/NCEeLHzADWp05cQzcuV13JgzT3XLJToLE2hvU+Yea+B21+Lozx8k2VN1Ry5sgHHNwEejZQdB04t89/1O/w1cDnyilFU=',
    'channelSecret' => 'd014d0127d2426dbbacd4065a35b80bc'
);

$request = file_get_contents("php://input");
$json = json_decode($request,true);

//SIGNATURE CHECK
$signature = $_SERVER['HTTP_X_LINE_SIGNATURE'];
if($signature!==base64_encode(hash_hmac('sha256',$request,$line['channelSecret'],true))){
    error_log('Signature check failed');
    http_response_code(400);
    exit(0);
}
//PASSED!
$image_base="https://chart.apis.google.com/chart?cht=tx&chs=50&chl=";

foreach($json['events'] as $e){
    $tex = '';
    switch($e['type']){
        case 'message':
        if($e['message']['type'] === 'text'){
            switch($e['source']['type']){
                case 'user':
                $tex = $e['message']['text'];
                break;
                case 'room':    //「招待」を使ったトーク
                case 'group':   //グループを作成したトーク
                if(stripos($e['message']['text'],'t:') === 0){
                    $tex = substr($e['message']['text'],2);
                }
                break;
            }
        }
        break;
    }

    $header = array(
        'Content-Type: application/json',
        'Authorization: Bearer ' . $line['accessToken']
    );

    //SEND IT!
    if($tex !== ''){
        $url = $image_base . urlencode('\displaystyle '.$tex);
        $body = array(
            'replyToken' => $e['replyToken'],
            'messages' => array(
                array(
                    'type' => 'image',
                    'originalContentUrl' => $url,
                    'previewImageUrl' => $url
                )
            )
        );
        $context = stream_context_create(array(
            'http' => array(
                'method' => 'POST',
                'header' => implode("\r\n",$header),
                'content' => json_encode($body)
            )
        ));

        $result = file_get_contents("https://api.line.me/v2/bot/message/reply",false,$context);
         if (strpos($http_response_header[0], '200') === false) {
            http_response_code(500);
            error_log("Request failed: " . $result);
        }
    }
}
