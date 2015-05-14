package com.mechzombie.continuum

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BoundaryTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BoundaryType.list(params), model:[boundaryTypeCount: BoundaryType.count()]
    }

    def show(BoundaryType boundaryType) {
        respond boundaryType
    }

    def create() {
        respond new BoundaryType(params)
    }

    @Transactional
    def save(BoundaryType boundaryType) {
        if (boundaryType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (boundaryType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond boundaryType.errors, view:'create'
            return
        }

        boundaryType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'boundaryType.label', default: 'BoundaryType'), boundaryType.id])
                redirect boundaryType
            }
            '*' { respond boundaryType, [status: CREATED] }
        }
    }

    def edit(BoundaryType boundaryType) {
        respond boundaryType
    }

    @Transactional
    def update(BoundaryType boundaryType) {
        if (boundaryType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (boundaryType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond boundaryType.errors, view:'edit'
            return
        }

        boundaryType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'boundaryType.label', default: 'BoundaryType'), boundaryType.id])
                redirect boundaryType
            }
            '*'{ respond boundaryType, [status: OK] }
        }
    }

    @Transactional
    def delete(BoundaryType boundaryType) {

        if (boundaryType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        boundaryType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'boundaryType.label', default: 'BoundaryType'), boundaryType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'boundaryType.label', default: 'BoundaryType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
