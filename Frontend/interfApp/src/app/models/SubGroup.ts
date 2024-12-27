import {User} from "./User";

export interface SubGroup {
  id: number;
  name: string;
  members: User[];
}
