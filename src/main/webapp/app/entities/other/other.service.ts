import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOTHER } from 'app/shared/model/other.model';

type EntityResponseType = HttpResponse<IOTHER>;
type EntityArrayResponseType = HttpResponse<IOTHER[]>;

@Injectable({ providedIn: 'root' })
export class OTHERService {
    private resourceUrl = SERVER_API_URL + 'api/others';

    constructor(private http: HttpClient) {}

    create(oTHER: IOTHER): Observable<EntityResponseType> {
        return this.http.post<IOTHER>(this.resourceUrl, oTHER, { observe: 'response' });
    }

    update(oTHER: IOTHER): Observable<EntityResponseType> {
        return this.http.put<IOTHER>(this.resourceUrl, oTHER, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOTHER>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOTHER[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
