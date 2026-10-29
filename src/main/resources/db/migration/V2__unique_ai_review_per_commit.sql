ALTER TABLE public.ai_reviews
ADD CONSTRAINT ai_reviews_pr_commit_unique
UNIQUE (pull_request_id, commit_sha);