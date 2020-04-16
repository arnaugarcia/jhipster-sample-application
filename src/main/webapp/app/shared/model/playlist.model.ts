import { ITrack } from 'app/shared/model/track.model';

export interface IPlaylist {
  id?: number;
  name?: string;
  collaborative?: boolean;
  description?: any;
  primaryColor?: string;
  cover?: string;
  thumbnail?: string;
  publicAccessible?: boolean;
  numberSongs?: number;
  followers?: number;
  rating?: number;
  tracks?: ITrack[];
}

export class Playlist implements IPlaylist {
  constructor(
    public id?: number,
    public name?: string,
    public collaborative?: boolean,
    public description?: any,
    public primaryColor?: string,
    public cover?: string,
    public thumbnail?: string,
    public publicAccessible?: boolean,
    public numberSongs?: number,
    public followers?: number,
    public rating?: number,
    public tracks?: ITrack[]
  ) {
    this.collaborative = this.collaborative || false;
    this.publicAccessible = this.publicAccessible || false;
  }
}
