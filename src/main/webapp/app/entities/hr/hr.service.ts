import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IHR } from 'app/shared/model/hr.model';

type EntityResponseType = HttpResponse<IHR>;
type EntityArrayResponseType = HttpResponse<IHR[]>;

@Injectable({ providedIn: 'root' })
export class HRService {
    private resourceUrl = SERVER_API_URL + 'api/hrs';

    constructor(private http: HttpClient) {}

    create(hR: IHR): Observable<EntityResponseType> {
        return this.http.post<IHR>(this.resourceUrl, hR, { observe: 'response' });
    }

    update(hR: IHR): Observable<EntityResponseType> {
        return this.http.put<IHR>(this.resourceUrl, hR, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IHR>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IHR[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
