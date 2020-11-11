export interface PagedSearchResponse<T> {
    content: T[],
    number: number,
    size: number,
    totalElements: number,
    pageable: {
        sort: {
            sorted: boolean,
            unsorted: boolean,
            empty: boolean
        },
        offset: 0,
        pageNumber: 0,
        pageSize: 20,
        paged: boolean,
        unpaged: boolean
    },
    last: boolean,
    totalPages: 1,
    sort: {
        sorted: boolean,
        unsorted: boolean,
        empty: boolean
    },
    first: boolean,
    numberOfElements: 4,
    empty: boolean
}