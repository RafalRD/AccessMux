import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISALE } from 'app/shared/model/sale.model';

type EntityResponseType = HttpResponse<ISALE>;
type EntityArrayResponseType = HttpResponse<ISALE[]>;

@Injectable({ providedIn: 'root' })
export class SALEService {
    private resourceUrl = SERVER_API_URL + 'api/sales';

    constructor(private http: HttpClient) {}

    create(sALE: ISALE): Observable<EntityResponseType> {
        return this.http.post<ISALE>(this.resourceUrl, sALE, { observe: 'response' });
    }

    update(sALE: ISALE): Observable<EntityResponseType> {
        return this.http.put<ISALE>(this.resourceUrl, sALE, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISALE>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISALE[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
