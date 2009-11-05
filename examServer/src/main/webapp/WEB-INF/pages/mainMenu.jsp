<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mainMenu.title"/></title>
    <content tag="heading"><fmt:message key="mainMenu.heading"/></content>
    <meta name="menu" content="MainMenu"/>
</head>

<p>Gratulation, Sie haben sich erfolgreich angemeldet! Als Administrator haben Sie folgende Möglichkeiten:</p>

<div class="separator"></div>

<ul class="glassList">
    <li>
        <a href="<c:url value="/LoginConfig.html"/>">Login-Konfiguration</a>
    </li>
    <li>
        <a href="<c:url value="/SystemConfig.html"/>">System-Konfiguration</a>
    </li>
    <li>
        <a href="<c:url value="/users.html"/>">Benutzer-Verwaltung</a>
    </li>
    <li>
        <a href="<c:url value="/TaskConfigList.html"/>">Aufgaben-Verwaltung</a>
    </li>

    <li>
        <a href="<c:url value="/tutorMainMenu.html"/>">Aufgaben-Korrektur</a>
    </li>
</ul>
