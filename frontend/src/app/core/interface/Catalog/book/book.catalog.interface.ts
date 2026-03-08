import { UUID } from "crypto";

export interface AuthorSnapshots{
  id : UUID;
  name : string;
}
export interface GenreSnapshots{
  id : UUID;
  name : string;
}

export interface GetBook{
  title : string;
  description : string;
  ISBN : string;
  price : number;
  authorIds : AuthorSnapshots[];
  genreIds: GenreSnapshots[];
  lifeCycleStatus: string;
  visibilityStatus: string;
  totalChapters: string;
  chapterIds: UUID[];
  freePreview : boolean;
}

export interface UpdateBookRequest{
  bookId : UUID;
  price : number;
  description: string;
  freePreviewAvailable: boolean;
  lifeCycleStatus: string;
  visibilityStatus: string;
}