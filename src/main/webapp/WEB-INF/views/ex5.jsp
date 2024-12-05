<%--
  Created by IntelliJ IDEA.
  User: it
  Date: 24. 12. 3.
  Time: 오전 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>ex5, 서버로부터 넘어온 더미 데이터 확인, 모델 객체없이, 스프링에서 파라미터 이름으로 넘어온 데이터를 화면에서 바로 사용해보기 </h1>
<h2>넘어온 데이터 todoDTO : <c:out value="${todoDTO}"></c:out> </h2>

</body>
</html>
