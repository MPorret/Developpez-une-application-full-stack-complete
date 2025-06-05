import { Topic } from "./topic.interface"
import { User } from "./user.interface"

export interface Subject {
    id: number,
    name: string,
    description: string,
    author: User,
    topic: Topic,
    created_at: Date,
    updated_at: Date
}