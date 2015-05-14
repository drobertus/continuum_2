<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'continuumType.label', default: 'ContinuumType')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-continuumType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-continuumType" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
                 <table>
                     <thead>
                          <tr>
                             <th class="sortable" ><a href="/continuumType/index?sort=name&amp;max=10&amp;order=asc">Name</a></th>
                             <th class="sortable" ><a href="/continuumType/index?sort=entryBoundary&amp;max=10&amp;order=asc">Entry Boundary</a></th>
                             <th class="sortable" ><a href="/continuumType/index?sort=exitBoundary&amp;max=10&amp;order=asc">Exit Boundary</a></th>
                             <th class="sortable" ><a href="/continuumType/index?sort=childTypes&amp;max=10&amp;order=asc">Child Types</a></th>
                             <th class="sortable" ><a href="/continuumType/index?sort=phaseTypes&amp;max=10&amp;order=asc">Phase Types</a></th>
                         </tr>
                     </thead>
                     <tbody>
                        <g:each in="${continuumTypeList}">
                         <tr class="even">
                             <td><a href="/continuumType/show/${it.ident()}">${it.name}</a></td>
                             <td>${it?.entryBoundary?.name}</td>
                             <td>${it?.exitBoundary?.name}</td>
                             <td>${it?.childTypes?.size()}</td>
                             <td>${it?.phaseTypes?.size()}</td>
                         </tr>
                         </g:each>
                     </tbody>
                 </table>

            <!-- f:table collection="${continuumTypeList}" / -->

            <div class="pagination">
                <g:paginate total="${continuumTypeCount ?: 0}" />
            </div>
        </div>
    </body>
</html>