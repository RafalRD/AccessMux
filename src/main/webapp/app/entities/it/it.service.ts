import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIT } from 'app/shared/model/it.model';

type EntityResponseType = HttpResponse<IIT>;
type EntityArrayResponseType = HttpResponse<IIT[]>;

@Injectable({ providedIn: 'root' })
export class ITService {
    private resourceUrl = SERVER_API_URL + 'api/its';

    constructor(private http: HttpClient) {}

    create(iT: IIT): Observable<EntityResponseType> {
        return this.http.post<IIT>(this.resourceUrl, iT, { observe: 'response' });
    }

    update(iT: IIT): Observable<EntityResponseType> {
        return this.http.put<IIT>(this.resourceUrl, iT, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IIT>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IIT[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
