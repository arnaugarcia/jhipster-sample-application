import { Moment } from 'moment';
import { IPlaylist } from 'app/shared/model/playlist.model';

export interface ITrack {
  id?: number;
  name?: string;
  rating?: number;
  url?: string;
  popularity?: string;
  thumbnail?: string;
  createdAt?: Moment;
  released?: Moment;
  duration?: number;
  color?: string;
  playlists?: IPlaylist[];
}

export class Track implements ITrack {
  constructor(
    public id?: number,
    public name?: string,
    public rating?: number,
    public url?: string,
    public popularity?: string,
    public thumbnail?: string,
    public createdAt?: Moment,
    public released?: Moment,
    public duration?: number,
    public color?: string,
    public playlists?: IPlaylist[]
  ) {}
}
