import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrack } from 'app/shared/model/track.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TrackService } from './track.service';
import { TrackDeleteDialogComponent } from './track-delete-dialog.component';

@Component({
  selector: 'jhi-track',
  templateUrl: './track.component.html'
})
export class TrackComponent implements OnInit, OnDestroy {
  tracks: ITrack[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected trackService: TrackService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tracks = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.trackService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ITrack[]>) => this.paginateTracks(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tracks = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTracks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrack): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTracks(): void {
    this.eventSubscriber = this.eventManager.subscribe('trackListModification', () => this.reset());
  }

  delete(track: ITrack): void {
    const modalRef = this.modalService.open(TrackDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.track = track;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTracks(data: ITrack[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tracks.push(data[i]);
      }
    }
  }
}
