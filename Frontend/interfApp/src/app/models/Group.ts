import {User} from "./User";
import {SubGroup} from "./SubGroup";

export interface Group {
  id: number;
  name: string;
  managers: User[];
  members: User[];
  subGroups: SubGroup[];
}
