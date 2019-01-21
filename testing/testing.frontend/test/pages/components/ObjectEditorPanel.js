class ObjectEditorPanel {
    get inputs() {
        return $$('input,textarea')
    }
    get textEditor() {
    	return $('.trumbowyg-editor')
    }
    get pathBrowserButtons() {
    	return this.container.$$('.btn-flat')
    }
    get checkboxLabel() {
        return this.container.$('.field-wrap label[for="checkbox"]')
    }
    get checkboxInput() {
    	return this.container.$('.field-material-checkbox input').getValue()
    }
    get radioButtons() {
    	const items = this.container.$$('.radio-list > li')
        return items.map(item => {
            return new RadioButton(item);
        })
    }
    get switchLabel() {
    	return this.container.$('.field-wrap label[for="material-switch"]')
    }
    get rangeInput() {
    	return this.container.$('.range-field input')
    }  
    get multiSelect() {
    	return new MultiSelect(this.container.$('.multiselect'))
    }
    get timeInput() {
    	return this.container.$('.field-material-timepicker input')
    }
    get dateInput() {
    	return this.container.$('.field-material-datepicker input')
    }
    get datetimeInput() {
    	return this.container.$('.field-material-datetime input')
    }
    
    // date time modal elements //
    get hours() {
    	const items = this.datetimeBrowserContainer.$$('.vdatetime-popup__list-picker--half:nth-child(1) > div')
        return items.map(item => {
            return new TimeItem(item);
        })
    }
    get minutes() {
    	const items = this.datetimeBrowserContainer.$$('.vdatetime-popup__list-picker:nth-child(2) > div')
        return items.map(item => {
            return new TimeItem(item);
        })
    }
    get days() {
    	const items = this.datetimeBrowserContainer.$$('.vdatetime-popup__date-picker > .vdatetime-popup__date-picker__item > span > span')
        return items.map(item => {
            return new DateItem(item);
        })
    }
    get monthSelector() {
    	return new MonthSelector(this.datetimeBrowserContainer.$('.vdatetime-popup__month-selector'))
    }
    get datetimeSaveButton() {
    	return this.datetimeBrowserContainer.$('.vdatetime-popup__actions__button:nth-child(2)') 
    }
    // date time modal elements //
    
    // pathbrowser modal elements
    get sites() {
        const items = this.pathBrowserContainer.$$('.modal-content > .col-browse > ul > li')
        return items.map(item => {
            return new Item(item);
        })
    }
    get selectedPath() {
    	return this.pathBrowserContainer.$('.modal-footer > .selected-path').getText()
    }
    get selectPathButton() {
    	return this.pathBrowserContainer.$('.modal-footer > button.modal-action:nth-child(3)')
    }
    // pathbrowser modal elements
    
    get collection() {
    	return new Collection(this.container.$('.field-collection'))
    } 
    
    get color() {
    	return new Color(this.container.$('.form-group:nth-child(18)')) 
    }
    
    get iconBrowser() {
    	return new IconBrowser(this.container.$('.field-iconbrowser'))
    }
    
    get iconBrowserContainer() {
    	return $('.modal-container')
    }
    
    // iconbrowser modal elements
    get icons() {
        const items = this.iconBrowserContainer.$$('.modal-content > .col-browse > .item-icon > .item-content') 
        return items.map(item => {
            return new Icon(item);
        })
    }
    get selectedIcon() {
    	return this.iconBrowserContainer.$('.modal-footer > .selected-path > .material-icons').getText()
    }
    get selectIconButton() {
    	return this.iconBrowserContainer.$('.modal-footer > button.modal-action:nth-child(3)') 
    }
    // iconbrowser modal elements
    
    get pathBrowserContainer() {
    	return $('.modal-container')
    }
    get datetimeBrowserContainer() {
    	return $('.vdatetime-popup')
    }
    get previewContainer() { return $('.vfg-preview') }
    get container() { return $('.vue-form-generator')}
    get buttonContainer() { return $('.explorer-confirm-dialog')}
    get save() {return this.buttonContainer.$('button[title="save object"]')}
}

class Item {
    constructor(container){
        this.container = container;
    }
    get text()     { return this.container.$(`span`).getText()}
    get label()    { return this.container.$(`label`)}
}

class Icon {
    constructor(container){
        this.container = container;
    }
    get text()     { return this.container.$(`span`).getText()}
    get label()    { return this.container.$(`i`)}
}

class RadioButton {
    constructor(container){
        this.container = container;
    }
    get text()     { return this.container.$(`input`).getValue()}
    get label()    { return this.container.$(`label`)}
}

class MultiSelect {
	constructor(container){
        this.container = container;
    }
	get selectButton() { return this.container.$('.multiselect__select')} 
	get contentWrapper() { return this.container.$('.multiselect__content-wrapper')}
	get tag() { return this.container.$('.multiselect__single')}
	get items(){
		const items = this.container.$$('ul > li')
        return items.map(item => {
            return new MultiSelectItem(item);
        })
	}
}

class MultiSelectItem {
    constructor(container){
        this.container = container;
    }
    get text()     { return this.container.$('.multiselect__option > span').getText()}
    get span()    { return this.container.$('.multiselect__option > span')}
}

class TimeItem {
    constructor(container){
        this.container = container;
    }
    get text()     { return this.container.getText()}
    get div()    { return this.container}
}

class DateItem {
    constructor(container){
        this.container = container;
    }
    get text()     { return this.container.getText()}
    get div()    { return this.container}
}

class MonthSelector {
	constructor(container){
        this.container = container;
    }
	get previousButton() { return this.container.$('.vdatetime-popup__month-selector__previous')} 
	get currentMonthYear() { return this.container.$('.vdatetime-popup__month-selector__current').getText()} 
	get nextButton() { return this.container.$('.vdatetime-popup__month-selector__next')} 

}

class Collection {
    constructor(container){
        this.container = container;
    }
    get addButton()     { return this.container.$('h5 > button')}
    get items()    { 
    	const items = this.container.$$('ul > li:not(.deleted)')
        return items.map(item => {
            return new CollectionItem(item);
        })
    }
}

class CollectionItem {
    constructor(container){
        this.container = container;
    }
    get title()   { return this.container.$('.field-input input')}
    get text()    { return this.container.$('.field-texteditor .trumbowyg-editor')} 
    get imageButton() {return this.container.$('.field-pathbrowser button') }
    get image()   { return this.container.$('.field-pathbrowser input')}
    get deleteButton() {return this.container.$('.delete-icon')}
    get header() {return this.container.$('span')}
}

class Color {
    constructor(container){
        this.container = container;
    }
    get button()     { return this.container.$('input')}
    get colorCode()  { return this.container.$('span')}
}

class IconBrowser {
    constructor(container){
        this.container = container;
    }
    get button()     { return this.container.$('button')}
    get text()  { return this.container.$('input')}
}


module.exports = ObjectEditorPanel