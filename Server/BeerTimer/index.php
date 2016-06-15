<html>
<head>
    <title>BeerTimer</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <script src="http://code.jquery.com/jquery-1.9.0.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#menu_bestusers").click(function() {
                $("#bestusers").css("display", "block");
                $("#besttimes").css("display", "none");
            });

            $("#menu_besttimes").click(function() {
                $("#bestusers").css("display", "none");
                $("#besttimes").css("display", "block");
            });
        });
    </script>


</head>
<body>
<div id="container">
    <div id="header">
        <div id="logo"></div>
    </div>
    <div id="middle">
        <div id="menu">
            <div class="menu_button" id="menu_besttimes">Best Times</div>
            <div class="menu_button" id="menu_bestusers">Best Users</div>
        </div>
        <div id="content">
            <div id="labels">
                <div class="label">User</div>
                <div class="label">Time</div>
            </div>
            <div id="bestusers">
                <?php
                    include 'db.php';


                    $result = mysqli_query($db, "SELECT Name, BestTime FROM users ORDER BY BestTime");
                    while($row = mysqli_fetch_assoc($result))
                        echo '<div class="entry"><div class="name">' . $row['Name'] . '</div><div class="time">' . $row['BestTime'] . '</div></div>';
                ?>
            </div>

            <div id="besttimes">
                <?php
                    include 'db.php';


                    $result = mysqli_query($db, "SELECT Name, Time FROM scores ORDER BY Time");
                    while($row = mysqli_fetch_assoc($result))
                        echo '<div class="entry"><div class="name">' . $row['Name'] . '</div><div class="time">' . $row['Time'] . '</div></div>';
                ?>
            </div>
        </div>
    </div>
    <div id="footer"></div>
</div>

</body>
</html>