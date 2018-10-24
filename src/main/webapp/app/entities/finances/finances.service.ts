import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFINANCES } from 'app/shared/model/finances.model';

type EntityResponseType = HttpResponse<IFINANCES>;
type EntityArrayResponseType = HttpResponse<IFINANCES[]>;

@Injectable({ providedIn: 'root' })
export class FINANCESService {
    private resourceUrl = SERVER_API_URL + 'api/finances';

    constructor(private http: HttpClient) {}

    create(fINANCES: IFINANCES): Observable<EntityResponseType> {
        return this.http.post<IFINANCES>(this.resourceUrl, fINANCES, { observe: 'response' });
    }

    update(fINANCES: IFINANCES): Observable<EntityResponseType> {
        return this.http.put<IFINANCES>(this.resourceUrl, fINANCES, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFINANCES>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFINANCES[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
