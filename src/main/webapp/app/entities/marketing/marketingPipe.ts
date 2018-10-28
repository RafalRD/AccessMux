import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
    name: 'marketingSafe'
})
export class MarketingPipe implements PipeTransform {
    constructor(private sanitizer: DomSanitizer) {}
    transform(url) {
        return this.sanitizer.bypassSecurityTrustResourceUrl(url);
    }
}
