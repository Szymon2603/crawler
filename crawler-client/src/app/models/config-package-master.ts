export class ConfigPackageMaster {

    constructor(
        public id: number,
        public name: string
    ) { }

    toString(): string {
        return JSON.stringify(this);
    }
}
