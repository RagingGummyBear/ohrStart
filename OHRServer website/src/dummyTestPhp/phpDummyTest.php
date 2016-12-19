<?php
require('../../vendor/autoload.php');
  use WebSocket\Client;
   
  session_start();
  print_r($_POST) . "HI";
  /*if(!isset($_SESSION['connected']))
  {
      */$client = new Client("ws://127.0.0.1:25565");
      $_SESSION['userIsConnected'] = $client;
  //}
  
 // print_r($_GET) . "HI";

 if(isset($_POST['action'])){
     //$client = $_SESSION['userIsConnected'];
//  echo $_POST['action']+'HI';
     
        if($_POST['action'] == 'registerAUser') {
           
      if($_POST['username'] && $_POST['password'] && $_POST['role']){
          
        $client->send('{"command":"registerUser","content":[' . $_POST['username'] .','. $_POST['password'] .',' . $_POST['role'] .']}');

        echo $client->receive(); 
      }
      // call removeday() here
    }
    
    if($_POST['action'] == 'loginUser') {
         
      if($_POST['username'] && $_POST['password']){
          echo $_POST['username'] . $_POST['password'];
        $client->send('{"command":"loginUser","content":[' . $_POST['username'] .','. $_POST['password'] . ']}');
            echo 'Hi';
        echo $client->receive(); 
      }
      // call removeday() here
    }
    
    
        
if($_POST['action'] == 'call_this') {


$client->send('{"command":"loginUser","userUniqueID":"Oslo West 16","content":["k00lTest","123 123 123","adminos"]}');

echo $client->receive(); 
    
  // call removeday() here
}

}
