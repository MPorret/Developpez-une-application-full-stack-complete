import { Comment } from './comment.model';

export interface Post {
  id: number;
  title: string;
  content: string;
  userId: number;
  authorName: string;
  topicId: number;
  comments: Comment[];
  createdAt: string;
  updatedAt: string;
}
