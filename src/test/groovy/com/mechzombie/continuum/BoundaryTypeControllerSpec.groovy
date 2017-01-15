package com.mechzombie.continuum

import grails.test.mixin.*
import spock.lang.*

@TestFor(BoundaryTypeController)
@Mock(BoundaryType)
class BoundaryTypeControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params["name"] = 'start'
        params["type"] = "Entry"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.boundaryTypeList
            model.boundaryTypeCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.boundaryType!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def boundaryType = new BoundaryType()
            boundaryType.validate()
            controller.save(boundaryType)

        then:"The create view is rendered again with the correct model"
            model.boundaryType!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            boundaryType = new BoundaryType(params)

            controller.save(boundaryType)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/boundaryType/show/1'
            controller.flash.message != null
            BoundaryType.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def boundaryType = new BoundaryType(params)
            controller.show(boundaryType)

        then:"A model is populated containing the domain instance"
            model.boundaryType == boundaryType
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def boundaryType = new BoundaryType(params)
            controller.edit(boundaryType)

        then:"A model is populated containing the domain instance"
            model.boundaryType == boundaryType
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/boundaryType/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def boundaryType = new BoundaryType()
            boundaryType.validate()
            controller.update(boundaryType)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.boundaryType == boundaryType

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            boundaryType = new BoundaryType(params).save(flush: true)
            controller.update(boundaryType)

        then:"A redirect is issued to the show action"
            boundaryType != null
            response.redirectedUrl == "/boundaryType/show/$boundaryType.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/boundaryType/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def boundaryType = new BoundaryType(params).save(flush: true)

        then:"It exists"
            BoundaryType.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(boundaryType)

        then:"The instance is deleted"
            BoundaryType.count() == 0
            response.redirectedUrl == '/boundaryType/index'
            flash.message != null
    }
}
