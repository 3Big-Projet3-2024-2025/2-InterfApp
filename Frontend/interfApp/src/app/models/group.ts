import {User} from "./User";

export interface Group {
  id: number;
  name: string;
  administrateur: User;
  membres: User[];
}
