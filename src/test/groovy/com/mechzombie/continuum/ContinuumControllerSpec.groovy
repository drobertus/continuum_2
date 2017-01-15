package com.mechzombie.continuum

import grails.test.mixin.*
import spock.lang.*

@TestFor(ContinuumController)
@Mock([Continuum, ContinuumType, Organization])
class ContinuumControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        params["name"] = 'conference Call 1'
        params["type"] = new ContinuumType(name: "type")

    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.continuumList
            model.continuumCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.continuum!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def continuum = new Continuum()
            continuum.validate()
            controller.save(continuum)

        then:"The create view is rendered again with the correct model"
            model.continuum!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            continuum = new Continuum(params)

            controller.save(continuum)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/continuum/show/1'
            controller.flash.message != null
            Continuum.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def continuum = new Continuum(params)
            controller.show(continuum)

        then:"A model is populated containing the domain instance"
            model.continuum == continuum
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def continuum = new Continuum(params)
            controller.edit(continuum)

        then:"A model is populated containing the domain instance"
            model.continuum == continuum
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/continuum/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def continuum = new Continuum()
            continuum.validate()
            controller.update(continuum)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.continuum == continuum

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            continuum = new Continuum(params).save(flush: true)
            controller.update(continuum)

        then:"A redirect is issued to the show action"
            continuum != null
            response.redirectedUrl == "/continuum/show/$continuum.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/continuum/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def continuum = new Continuum(params).save(flush: true)

        then:"It exists"
            Continuum.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(continuum)

        then:"The instance is deleted"
            Continuum.count() == 0
            response.redirectedUrl == '/continuum/index'
            flash.message != null
    }
}
