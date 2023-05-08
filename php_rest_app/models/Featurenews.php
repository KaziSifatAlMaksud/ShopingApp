<?php
  class Featurenews {
    // DB Stuff
    private $conn;
    private $table = 'featurenews';

    // Properties
    public $id;
    public $title;
    public $image;
    public $brief_content;
    public $draft;
    public $status;



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
      $this->title = $row['title'];
      $this->image = $row['image'];
      $this->brief_content = $row['brief_content'];
      $this->draft = $row['draft'];
      $this->status = $row['status'];

  }

  // Create Category
  public function create() {
    // Create Query
    $query = 'INSERT INTO ' .
      $this->table . 'SET
      title = :title, image = :image, brief_content= :brief_content,
      draft = :draft,  status=:status';
      

  // Prepare Statement
  $stmt = $this->conn->prepare($query);

  // Clean data
  $this->title = htmlspecialchars(strip_tags($this->title));
  $this->image = htmlspecialchars(strip_tags($this->image));
  $this->brief_content = htmlspecialchars(strip_tags($this->brief_content));
  $this->draft  = htmlspecialchars(strip_tags($this->draft ));
  $this->status = htmlspecialchars(strip_tags($this->status));

  // Bind data
  $stmt-> bindParam(':title', $this->title);
  $stmt-> bindParam(':image', $this->image);
  $stmt-> bindParam(':brief_content', $this->brief_content);
  $stmt-> bindParam(':draft', $this->draft);
  $stmt-> bindParam(':status', $this->status);


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
    title = :title, image = :image, bried_content = :bried_content,
      draft = :draft,  status=:status
      WHERE
      id = :id';

  // Prepare Statement
  $stmt = $this->conn->prepare($query);

  // Clean data
  $this->title = htmlspecialchars(strip_tags($this->title));
  $this->image = htmlspecialchars(strip_tags($this->image));
  $this->brief_content = htmlspecialchars(strip_tags($this->brief_content));
  $this->draft  = htmlspecialchars(strip_tags($this->draft ));
  $this->status = htmlspecialchars(strip_tags($this->status));



  // Bind data
  $stmt-> bindParam(':name', $this->name);
  $stmt-> bindParam(':image', $this->image);
  $stmt-> bindParam(':brief_content', $this->brief_content);
  $stmt-> bindParam(':draft', $this->draft);
  $stmt-> bindParam(':status', $this->status);


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
