<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <title>Check your number!</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
</head>
<body>
<div class="wrapper">
    <div class="content">
        <h2 class="contentHeader">Check if your number is a prime!</h2>
        <form action="primecheck" method="POST">
            <input type="text" name="number" placeholder="Enter number"/>
            <input type="submit" value="Go!"/>
        </form>
        <%
            String result = (String) request.getAttribute("result");
            if (result != null) {
                out.println(result);
            }
        %>
    </div>
</div>
</body>
</html>
