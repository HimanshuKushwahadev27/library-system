import { UUID } from "crypto";

export interface GetChapter{
  id : UUID;
  bookid : UUID;
  title : string;
  chapterNumber : number;
  price : number;
  content: string;
  freePreview: boolean;
  createdAt : Date;
  updatedAt: Date;
  status: string;
}