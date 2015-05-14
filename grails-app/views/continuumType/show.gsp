<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'continuumType.label', default: 'ContinuumType')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-continuumType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="show-continuumType" class="content scaffold-show" role="main">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <div id="show-continuumType" class="content scaffold-show" role="main">

                        <ol class="property-list continuumType">

                    <li class="fieldcontain">
                        <span id="name-label" class="property-label">Name</span>
                        <span class="property-value" aria-labelledby="name-label">${continuumType.name}</span>
                    </li>

                    <g:if test="${continuumType.entryBoundary}">
                    <li class="fieldcontain">
                        <span id="entryBoundary-label" class="property-label">Entry Boundary</span>
                        <span class="property-value" aria-labelledby="entryBoundary-label">${continuumType.entryBoundary.name}</span>
                    </li>
                    </g:if>

                    <g:if test="${continuumType.exitBoundary}">
                    <li class="fieldcontain">
                        <span id="exitBoundary-label" class="property-label">Exit Boundary</span>
                        <span class="property-value" aria-labelledby="exitBoundary-label">${continuumType.exitBoundary.name}</span>
                    </li>
                    </g:if>

                    <li class="fieldcontain">
                        <span id="phaseTypes-label" class="property-label">Phase Types</span>
                        <span class="property-value" aria-labelledby="phaseTypes-label">
                        <g:select name="phaseTypes" id="phaseTypes"
                                                multiple="true"
                                                from="${continuumType.phaseTypes}"
                                                value="${continuumType?.exitBoundary?.id}"
                                                optionKey="id" optionValue="name" />
                        </span>
                    </li>

            </ol>
            <!-- <f:display bean="continuumType" /> -->
           <!-- </ol> -->
            <g:form resource="${continuumType}" method="DELETE">
                <fieldset class="buttons">
                    <g:link class="edit" action="edit" resource="${continuumType}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>