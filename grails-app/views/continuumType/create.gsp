<!DOCTYPE html>
<%@ page import="com.mechzombie.continuum.*" %>
<html>
    <head>
        <meta name="layout" content="main">
        <g:set var="entityName" value="${message(code: 'continuumType.label', default: 'ContinuumType')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-continuumType" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-continuumType" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${continuumType}">
            <ul class="errors" role="alert">
                <g:eachError bean="${continuumType}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form action="save">

   <g:hiddenField name="version" value="${continuumType?.version}" />
   <g:hiddenField name="parentId" value="${continuumType?.parentId}" />
                <fieldset class="form">

              <div class='fieldcontain required'>
                <label for='name'>Name<span class='required-indicator'>*</span></label>
                <g:textField name="name" value="${continuumType.name}" />
              </div>

                <g:set var="entryBoundaryTypeList" value="${BoundaryType.findWhere(type: "Entry")}"/>
                <g:set var="exitBoundaryTypeList" value="${BoundaryType.findWhere(type: "Exit")}"/>

                <div class='fieldcontain'>
                  <label for='entryBoundary'>Entry Boundary</label>
                  <g:select name="entryBoundary.id"
                            noSelection="${['null':'None']}"
                            from="${entryBoundaryTypeList}"
                            value="${continuumType?.entryBoundary?.id}"
                            optionKey="id" optionValue="name" />

                </div>

                <div class='fieldcontain'>
                  <label for='exitBoundary'>Exit Boundary</label>
                  <g:select name="exitBoundary.id" id="exitBoundary"
                       noSelection="${['null':'None']}"
                       from="${exitBoundaryTypeList}"
                       value="${continuumType?.exitBoundary?.id}"
                       optionKey="id" optionValue="name" />
                </div>

                <div class='fieldcontain'>
                  <label for='childTypes'>Child Continuum Types</label>
                  <ul></ul>
                    <g:link action="create" controller="continuumType" id="${continuumType.id}">Add Child Continuum Type</g:link>
                </div>

                <div class='fieldcontain'>
                  <label for='phaseTypes'>Phase Types</label>
                  <ul></ul>
                   <g:link action="create" controller="phaseType" id="${continuumType.id}">Add PhaseType</g:link>

                </div>
                    <!-- f:all bean="continuumType"/ -->


                    <!-- f:all bean="continuumType"/ -->
                </fieldset>
                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
