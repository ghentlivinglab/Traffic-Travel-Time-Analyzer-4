<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/shared/head.jsp">
            <jsp:param name="title" value="Contact" />
        </jsp:include>
    </head>
    <body>
        <jsp:include page="/WEB-INF/shared/navigation.jsp" />
        <div class="container">

            <h1>Providers:</h1>
            <ul>
                <li>Google maps</li>
                <li>Waze</li>
                <li>Here</li>
                <li>Coyote</li>
                <li>TomTom</li>
            </ul>
            
        </div>
    </body>
</html>
