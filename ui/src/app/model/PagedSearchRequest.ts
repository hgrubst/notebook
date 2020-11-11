export class PagedSearchRequest {
    size : number = 20;
    page : number = 0;
    sort : string[] //eg name or name,desc (default is asc)
}