import {  UUID } from "crypto";

export interface GenreCreate{
 name: string;
 description: string;
}

export interface GenreResponse{
  genreId : UUID;
  name: string;
  description: string;
}