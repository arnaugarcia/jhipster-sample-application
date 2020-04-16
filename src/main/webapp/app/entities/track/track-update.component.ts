import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITrack, Track } from 'app/shared/model/track.model';
import { TrackService } from './track.service';

@Component({
  selector: 'jhi-track-update',
  templateUrl: './track-update.component.html'
})
export class TrackUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    rating: [],
    url: [],
    popularity: [],
    thumbnail: [],
    createdAt: [],
    released: [],
    duration: [],
    color: []
  });

  constructor(protected trackService: TrackService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ track }) => {
      if (!track.id) {
        const today = moment().startOf('day');
        track.createdAt = today;
        track.released = today;
      }

      this.updateForm(track);
    });
  }

  updateForm(track: ITrack): void {
    this.editForm.patchValue({
      id: track.id,
      name: track.name,
      rating: track.rating,
      url: track.url,
      popularity: track.popularity,
      thumbnail: track.thumbnail,
      createdAt: track.createdAt ? track.createdAt.format(DATE_TIME_FORMAT) : null,
      released: track.released ? track.released.format(DATE_TIME_FORMAT) : null,
      duration: track.duration,
      color: track.color
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const track = this.createFromForm();
    if (track.id !== undefined) {
      this.subscribeToSaveResponse(this.trackService.update(track));
    } else {
      this.subscribeToSaveResponse(this.trackService.create(track));
    }
  }

  private createFromForm(): ITrack {
    return {
      ...new Track(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      url: this.editForm.get(['url'])!.value,
      popularity: this.editForm.get(['popularity'])!.value,
      thumbnail: this.editForm.get(['thumbnail'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      released: this.editForm.get(['released'])!.value ? moment(this.editForm.get(['released'])!.value, DATE_TIME_FORMAT) : undefined,
      duration: this.editForm.get(['duration'])!.value,
      color: this.editForm.get(['color'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrack>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
