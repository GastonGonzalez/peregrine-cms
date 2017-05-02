// var axios = require('axios')

import { LoggerFactory } from './logger'
let logger = LoggerFactory.logger('apiImpl').setLevelDebug()

const API_BASE = '/api'

let callbacks

function fetch(path) {

    logger.fine('fetch() ', path)
    return axios.get(API_BASE+path).then( (response) => {
        return new Promise( (resolve, reject) => {
            resolve(response.data)
        })
    }).catch( (error) => { logger.error('request to',
            error.response.request.path, 'failed')
            throw error
        })

}

function getOrCreate(obj, path) {

    if(path === '/') {
        // do nothing, requesting root
    }
    else {
        var segments = path.split('/').slice(1).reverse()

        while(segments.length > 0) {
            var segment = segments.pop()
            if(!obj[segment]) {
                obj[segment] = {}
            }
            obj = obj[segment]
        }
    }

    return obj
}

function populateView(path, name, data) {

    return new Promise( (resolve, reject) => {
        var obj = getOrCreate(callbacks.getView(), path)
        obj[name] = data
        resolve()
    })

}


class PerAdminImpl {

    constructor(cb) {
        callbacks = cb
    }

    populateTools() {
        return new Promise( (resolve, reject) => {
            fetch('/admin/list.json/tools')
                .then( (data) => populateView('/admin', 'tools', data.children) )
                .then(() => resolve() )
                .catch( (error) => {
                    logger.error('call populateTools() failed')
                    reject(error)
                })
        })
    }

    populateToolsConfig() {
        return new Promise( (resolve, reject) => {
            fetch('/admin/list.json/tools/config')
                .then( (data) => populateView('/admin', 'toolsConfig', data.children) )
                .then(() => resolve() )
        })
    }

    populateUser() {
        return new Promise( (resolve, reject) => {
            fetch('/admin/access.json')
                .then( (data) => populateView('/state', 'user', data.userID) )
                .then(() => resolve() )
        })
    }

    populateContent(path) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/content.json'+path)
                .then( (data) => populateView('/', 'adminPageStaged', data) )
                .then(() => resolve() )
        })
    }

    populateComponents() {
        return new Promise( (resolve, reject) => {
            fetch('/admin/components')
                .then( (data) => populateView('/admin', 'components', data) )
                .then( () => resolve() )
        })
    }

    populateNodesForBrowser(path, includeParents = false) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/nodes.json/path//'+path+'//includeParents//'+includeParents)
                .then( (data) => populateView('/admin', 'nodes', data) )
                .then(() => resolve() )
        })
    }

    populateComponentDefinition(component) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/components/'+component)
                .then( (data) => populateView('/admin/componentDefinitions', component, data) )
                .then( () => resolve() )
        })
    }

    populateComponentDefinitionFromNode(path) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/componentDefinition/path//'+path)
                .then( (data) => populateView('/admin/componentDefinitions', data.name, data.config) )
                .then( () => resolve() )
        })
    }

    createPage(parentPath, name, templatePath) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/createPage.json/path//'+parentPath+'//name//'+name+'//templatePath//'+templatePath)
                .then( (data) => this.populateNodesForBrowser(parentPath) )
                .then( () => resolve() )
        })
    }

    deletePage(path) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/deletePage.json/path//'+path)
                .then( (data) => this.populateNodesForBrowser(path) )
                .then( () => resolve() )
        })
    }

    createTemplate(parentPath, name) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/createTemplate.json/path//'+parentPath+'//name//'+name)
                .then( (data) => this.populateNodesForBrowser(parentPath) )
                .then( () => resolve() )
        })
    }

    createFolder(parentPath, name) {
        return new Promise( (resolve, reject) => {
            fetch('/admin/createFolder.json/path//'+parentPath+'//name//'+name)
                .then( (data) => this.populateNodesForBrowser(parentPath) )
                .then( () => resolve() )
        })
    }

    uploadFiles(path, files) {
        return new Promise( (resolve, reject) => {

                logger.fine('uploading files to', path)
                logger.fine(files)

                var data = new FormData()
                for(var i = 0; i < files.length; i++) {
                    var file = files[i]
                    data.append(file.name, file, file.name)
                }

                axios.post(API_BASE+'/admin/uploadFiles.json/path//'+path, data).then( (response) => {
                        logger.fine(response.data)
                        this.populateNodesForBrowser(path) })
                    .then( () => resolve() )
        })
    }
}

export default PerAdminImpl