package com.mechzombie.continuum

import grails.test.mixin.*
import spock.lang.*

@TestFor(PhaseTypeController)
@Mock([PhaseType, ContinuumType, Organization])
class PhaseTypeControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params["name"] = 'pre-conference'
        params["order"] = 2
        params["organization"] = new Organization(name: "org1")
        params["type"] = "mandatory"
        params["continuumType"] = new ContinuumType(name: "meeting")
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.phaseTypeList
            model.phaseTypeCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.phaseType!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def phaseType = new PhaseType()
            phaseType.validate()
            controller.save(phaseType)

        then:"The create view is rendered again with the correct model"
            model.phaseType!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            phaseType = new PhaseType(params)

            controller.save(phaseType)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/phaseType/show/1'
            controller.flash.message != null
            PhaseType.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def phaseType = new PhaseType(params)
            controller.show(phaseType)

        then:"A model is populated containing the domain instance"
            model.phaseType == phaseType
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def phaseType = new PhaseType(params)
            controller.edit(phaseType)

        then:"A model is populated containing the domain instance"
            model.phaseType == phaseType
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/phaseType/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def phaseType = new PhaseType()
            phaseType.validate()
            controller.update(phaseType)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.phaseType == phaseType

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            phaseType = new PhaseType(params).save(flush: true)
            controller.update(phaseType)

        then:"A redirect is issued to the show action"
            phaseType != null
            response.redirectedUrl == "/phaseType/show/$phaseType.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/phaseType/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def phaseType = new PhaseType(params).save(flush: true)

        then:"It exists"
            PhaseType.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(phaseType)

        then:"The instance is deleted"
            PhaseType.count() == 0
            response.redirectedUrl == '/phaseType/index'
            flash.message != null
    }
}
