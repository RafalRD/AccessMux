import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMARKETING } from 'app/shared/model/marketing.model';

type EntityResponseType = HttpResponse<IMARKETING>;
type EntityArrayResponseType = HttpResponse<IMARKETING[]>;

@Injectable({ providedIn: 'root' })
export class MARKETINGService {
    private resourceUrl = SERVER_API_URL + 'api/marketings';

    constructor(private http: HttpClient) {}

    create(mARKETING: IMARKETING): Observable<EntityResponseType> {
        return this.http.post<IMARKETING>(this.resourceUrl, mARKETING, { observe: 'response' });
    }

    update(mARKETING: IMARKETING): Observable<EntityResponseType> {
        return this.http.put<IMARKETING>(this.resourceUrl, mARKETING, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMARKETING>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMARKETING[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
