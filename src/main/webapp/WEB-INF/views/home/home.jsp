<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
<nav >
    <a href="#">My App</a>
    <button>
        <span ></span>
    </button>
    <div id="navbarNav">
        <ul >
            <li >
                <a href="#">Home <span >(current)</span></a>
            </li>
            <li >
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
        </ul>
    </div>
</nav>
<div >
    <h1>Welcome, ${sessionScope.username}!</h1>
    <p>You are now logged in.</p>
</div>
</body>
</html>