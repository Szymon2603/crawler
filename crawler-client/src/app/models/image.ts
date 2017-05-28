export class Image {
    constructor(
        public id?: number,
        public imageURL?: string,
        public numberOfComments?: number,
        public rating?: number,
        public createDate?: Date,
    ) { }

    toString(): string {
        return JSON.stringify(this);
    }
}
