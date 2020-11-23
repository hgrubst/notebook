import { PagedSearchRequest } from './PagedSearchRequest';

export class NotebookSearchRequest extends PagedSearchRequest {
    id: string;
    userEmail: string;
}