<?php
  class Products {
    // DB Stuff
    private $conn;
    private $table = 'products';

    // Properties
    public $id;
    public $name;
    public $image;
    public $price;
    public $price_discount;
    public $stock;
    public $draft;
    public $status;
    public $created_at;
    public $last_update;


    // Constructor with DB
    public function __construct($db) {
      $this->conn = $db;
    }

    // Get categories
    public function read() {
      // Create query
      $query = 'SELECT
        *
      FROM
        ' . $this->table . '
      ORDER BY
        id DESC';

      // Prepare statement
      $stmt = $this->conn->prepare($query);

      // Execute query
      $stmt->execute();

      return $stmt;
    }

    // Get Single Category
  public function read_single(){
    // Create query
    $query = 'SELECT
          *
        FROM
          ' . $this->table . '
      WHERE id = ?
      LIMIT 0,1';

      //Prepare statement
      $stmt = $this->conn->prepare($query);

      // Bind ID
      $stmt->bindParam(1, $this->id);

      // Execute query
      $stmt->execute();

      $row = $stmt->fetch(PDO::FETCH_ASSOC);

      // set properties
      $this->id = $row['id'];
      $this->name = $row['name'];
      $this->image = $row['image'];
      $this->price = $row['price'];
      $this->price_discount = $row['price_discount'];
      $this->stock = $row['stock'];
      $this->draft = $row['draft'];
      $this->status = $row['status'];
      $this->created_at = $row['created_at'];
      $this->last_update = $row['last_update'];
  }

  // Create Category
  public function create() {
    // Create Query
    $query = 'INSERT INTO ' .
      $this->table . 'SET
      name = :name, image = :image, price = :price,price_discount= :price_discount,stock = :stock,
      draft = :draft,  status=:status, created_at =:created_at, last_update=:last_update';
      

  // Prepare Statement
  $stmt = $this->conn->prepare($query);

  // Clean data
  $this->name = htmlspecialchars(strip_tags($this->name));
  $this->image = htmlspecialchars(strip_tags($this->image));
  $this->price = htmlspecialchars(strip_tags($this->price));
  $this->price_discount = htmlspecialchars(strip_tags($this->price_discount));
  $this->stock = htmlspecialchars(strip_tags($this->stock));
  $this->draft  = htmlspecialchars(strip_tags($this->draft ));
  $this->status = htmlspecialchars(strip_tags($this->status));
  $this->created_at  = htmlspecialchars(strip_tags($this->created_at ));
  $this->last_update = htmlspecialchars(strip_tags($this->last_update));

  // Bind data
  $stmt-> bindParam(':name', $this->name);
  $stmt-> bindParam(':image', $this->image);
  $stmt-> bindParam(':price', $this->price);
  $stmt-> bindParam(':price_discount', $this->price_discount);
  $stmt-> bindParam(':stock', $this->stock);
  $stmt-> bindParam(':draft', $this->draft);
  $stmt-> bindParam(':status', $this->status);
  $stmt-> bindParam(':created_at', $this->created_at);
  $stmt-> bindParam(':last_update', $this->last_update);


  // Execute query
  if($stmt->execute()) {
    return true;
  }

  // Print error if something goes wrong
  printf("Error: $s.\n", $stmt->error);

  return false;
  }

  // Update Category
  public function update() {
    // Create Query
    $query = 'UPDATE ' .
      $this->table . '
    SET
    name = :name, image = :image, price = :price,price_discount= :price_discount,stock = :stock,
      draft = :draft,  status=:status, created_at =:created_at, last_update=:last_update
      WHERE
      id = :id';

  // Prepare Statement
  $stmt = $this->conn->prepare($query);

  // Clean data
  $this->name = htmlspecialchars(strip_tags($this->name));
  $this->image = htmlspecialchars(strip_tags($this->image));
  $this->price = htmlspecialchars(strip_tags($this->price));
  $this->price_discount = htmlspecialchars(strip_tags($this->price_discount));
  $this->stock = htmlspecialchars(strip_tags($this->stock));
  $this->draft  = htmlspecialchars(strip_tags($this->draft ));
  $this->status = htmlspecialchars(strip_tags($this->status));
  $this->created_at  = htmlspecialchars(strip_tags($this->created_at ));
  $this->last_update = htmlspecialchars(strip_tags($this->last_update));


  // Bind data
  $stmt-> bindParam(':name', $this->name);
  $stmt-> bindParam(':image', $this->image);
  $stmt-> bindParam(':price', $this->price);
  $stmt-> bindParam(':price_discount', $this->price_discount);
  $stmt-> bindParam(':stock', $this->stock);
  $stmt-> bindParam(':draft', $this->draft);
  $stmt-> bindParam(':status', $this->status);
  $stmt-> bindParam(':created_at', $this->created_at);
  $stmt-> bindParam(':last_update', $this->last_update);

  // Execute query
  if($stmt->execute()) {
    return true;
  }

  // Print error if something goes wrong
  printf("Error: $s.\n", $stmt->error);

  return false;
  }

  // Delete Category
  public function delete() {
    // Create query
    $query = 'DELETE FROM ' . $this->table . ' WHERE id = :id';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // clean data
    $this->id = htmlspecialchars(strip_tags($this->id));

    // Bind Data
    $stmt-> bindParam(':id', $this->id);

    // Execute query
    if($stmt->execute()) {
      return true;
    }

    // Print error if something goes wrong
    printf("Error: $s.\n", $stmt->error);

    return false;
    }
  }
