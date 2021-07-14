# FCM_2021
FCM PHP api

<?php
if(array_key_exists('button1', $_POST))
    {
        
        $title = 'My Notification Title';
        $body = 'My Notification Body';

        $imageUrl = 'https://news.mm-link.net/uploads/posts/2192197104-07-2021-09-49.jpg'; 
        //image url=http://lorempixel.com/400/200/
       
        $fcmUrl = 'https://fcm.googleapis.com/fcm/send';

        $notification = [
            'title' => $title,
            'sound' => true,
            'body' => $body,
            'image' => $imageUrl,
            'click_action'=>'com.linn.solution.fcm_2021.FCM_NOTIFICATION_DETAIL',
        ];

        $data = [
            'title' => $title,
            'body' => $body,
            'image' => $imageUrl,
        ];


        $fcmNotification = [
            'to'        => "/topics/news", 
            'notification' => $notification,
            'data' => $data,
        ];

        $headers = [
            'Authorization: key=AAAANAuqC5k:APA91bHa6VBVqCYK6WPJBqAKYUlk4Pc14TFyBJXftBjASWHLtkrPxxjaIIR4uIW9Xke_RPclDHeZOP5ESInrkNii8jQdmhlx3IeBAhhXj4-pTsejzXRpTOg_QymSrR0RDKPW7ptsVMkA',
            'Content-Type: application/json'
        ];


        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL,$fcmUrl);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fcmNotification));
        $result = curl_exec($ch);
        curl_close($ch);

        return true;
    }
?>

<!DOCTYPE html>
<html>
<body>

<form method="post">
        <input type="submit" name="button1"
                class="button" value="Button1" />
          
        <input type="submit" name="button2"
                class="button" value="Button2" />
    </form>

</body>
</html>
