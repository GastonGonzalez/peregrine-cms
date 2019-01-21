class Explorer {
    get sites() {
        const items = $$('.explorer-main > ul > li')
        return items.map(item => {
            return new Item(item);
        })
    }
    get objects() {
        const items = $$('.explorer-main > ul > li')
        return items.map(item => {
            return new Item(item);
        })
    }
    get assets() {
        const items = $$('.explorer-main > ul > li')
        return items.map(item => {
            return new AssetItem(item);
        })
    }
    get folders() {
        const items = $$('.explorer-main > ul > li')
        return items.map(item => {
            return new FolderItem(item);
        })
    }
    get container() { return $('.explorer-main') }
}

class Item {
    constructor(container){
        this.container = container;
    }
    get text()     { return this.container.$(`span > a`).getText()}
    get editButton()    { return this.container.$(`div > span:nth-child(1) > a`) }
    get deleteButton()   { return this.container.$(`div > span:nth-child(5) > a`) }
}

class FolderItem {
	constructor(container){
        this.container = container;
    }
    get text()     { return this.container.$(`span > a`).getText()}
    get linkButton()    { return this.container.$(`span > a`) }
}

class AssetItem {
	constructor(container){
        this.container = container;
    }
    get text()     { return this.container.$(`span > a`).getText()}
    get linkButton()    { return this.container.$(`span > a`) }
}
module.exports = Explorer