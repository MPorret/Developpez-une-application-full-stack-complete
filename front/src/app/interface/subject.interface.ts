import { Topic } from "./topic.interface"
import { User } from "./user.interface"

export interface Subject {
    id: number,
    name: string,
    description: string,
    author: User,
    topic: Topic,
    comments: Comment[],
    created_at: Date,
    updated_at: Date
}

export interface Comment {
    id: number,
    comment: string,
    author: User,
    created_at: Date,
    updated_at: Date
}