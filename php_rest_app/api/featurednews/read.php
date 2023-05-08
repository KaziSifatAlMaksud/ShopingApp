<?php 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../config/Database.php';
  include_once '../../models/Featurenews.php';

  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate category object
  $featurenews = new featurenews($db);

  // Category read query
  $result = $featurenews->read();
  
  // Get row count
  $num = $result->rowCount();

  // Check if any categories
  if($num > 0) {
        // Cat array
        $featurenews_arr = array();
       // $produ_arr['data'] = array();

        while($row = $result->fetch(PDO::FETCH_ASSOC)) {
          extract($row);

          $featurenews_item = array(
            'id' => $id,
            'title' => $title,
            'image' => $image,
            'brief_content' => $brief_content,
            'draft' => $draft,          
            'status' => $status,
                 
          );

          // Push to "data"
          array_push($featurenews_arr, $featurenews_item);
          //array_push($produ_arr['data'], $produ_item);
        }

        // Turn to JSON & output
        echo json_encode($featurenews_arr);

  } else {
        // No Categories
        echo json_encode(
          array('message' => 'No Categories Found')
        );
  }
