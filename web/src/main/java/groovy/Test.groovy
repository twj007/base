package groovy

import groovy.text.SimpleTemplateEngine

class Test {

    static void main(String[] args){
       new Test().doTemplate()
    }

    def doTemplate(){
        def template = 'it\'s a template from "$name"'
        def data = ["name": "joe"]
        def engine = new SimpleTemplateEngine()
        def result = engine.createTemplate(template).make(data)
        println result
        return result
    }
}
