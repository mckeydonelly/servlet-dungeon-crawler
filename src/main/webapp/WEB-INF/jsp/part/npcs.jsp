<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${npcs}" var="npc">
    <div id="npc${npc.getNpcId()}card" class="card mb-1 text-white bg-secondary">
        <img class="card-img-top"
             src="${npc.getImgPath()}"
             alt="Card image">
        <div class="card-body">
            <h4 class="card-title">${npc.getName()}</h4>
            <a class="btn btn-dark"
               href="${pageContext.request.contextPath}/attack?npcId=${npc.getNpcId()}&mapId=${npc.getMapId()}">Attack</a>
            <a class="btn btn-dark"
               href="${pageContext.request.contextPath}/speak?npcId=${npc.getNpcId()}&phraseId=1">Speak</a>
        </div>
    </div>
</c:forEach>
