import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IACTIVITY } from 'app/shared/model/activity.model';

type EntityResponseType = HttpResponse<IACTIVITY>;
type EntityArrayResponseType = HttpResponse<IACTIVITY[]>;

@Injectable({ providedIn: 'root' })
export class ACTIVITYService {
    private resourceUrl = SERVER_API_URL + 'api/activities';

    constructor(private http: HttpClient) {}

    create(aCTIVITY: IACTIVITY): Observable<EntityResponseType> {
        return this.http.post<IACTIVITY>(this.resourceUrl, aCTIVITY, { observe: 'response' });
    }

    update(aCTIVITY: IACTIVITY): Observable<EntityResponseType> {
        return this.http.put<IACTIVITY>(this.resourceUrl, aCTIVITY, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IACTIVITY>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IACTIVITY[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
