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
            <h1>Contact</h1>

            <h2>Teammembers:</h2>
            <ul>
                <li>Tomas Bolckmans</li>
                <li>Thomas Clauwaert</li>
                <li>Dwight Kerkhove</li>
                <li>Aaron Mousavi</li>
                <li>Niels Verbeeck</li>
                <li>Jaron Vervynckt</li>
            </ul>
            
        </div>
    </body>
</html>
