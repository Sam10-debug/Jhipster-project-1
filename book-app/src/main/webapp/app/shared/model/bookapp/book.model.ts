export interface IBook {
  id?: number;
  title?: string;
  author?: string;
  price?: number;
  isbn?: string;
}

export const defaultValue: Readonly<IBook> = {};
