<?php 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../config/Database.php';
  include_once '../../models/Products.php';

  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate category object
  $products = new Products($db);

  // Category read query
  $result = $products->read();
  
  // Get row count
  $num = $result->rowCount();

  // Check if any categories
  if($num > 0) {
        // Cat array
        $produ_arr = array();
       // $produ_arr['data'] = array();

        while($row = $result->fetch(PDO::FETCH_ASSOC)) {
          extract($row);

          $produ_item = array(
            'id' => $id,
            'name' => $name,
            'image' => $image,
            'price' => $price,
            'price_discount' => $price_discount,
            'stock' => $stock,
            'draft' => $draft,
            'status' => $status,
            'created_at' => $created_at,
            'last_update' => $last_update            
          );

          // Push to "data"
          array_push($produ_arr, $produ_item);
          //array_push($produ_arr['data'], $produ_item);
        }

        // Turn to JSON & output
        echo json_encode($produ_arr);

  } else {
        // No Categories
        echo json_encode(
          array('message' => 'No Categories Found')
        );
  }
