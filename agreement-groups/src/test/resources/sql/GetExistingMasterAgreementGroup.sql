
-- We need a customer :
--    that a sales executive has access to
--    who has a master agreement group
--    who has an ungrouped working quote

select top 1 gr.ki_grcont, gr.ka_acteur, gr.k_societe, count(1) 
from acgrcont gr
join dogrcont dogr on dogr.k_nogrcont = gr.ki_grcont
where fl_master_agreement = 1
and gr.ka_acteur in (select distinct ka_client from dossiers where ka_vendeur = :salesExecutiveActorCode)
and exists (
	select 1 
	from dossiers dos 
	where ka_client = gr.ka_acteur and k_societe = gr.k_societe 
	and kcd_statdos = 1 
	and not exists (select 1 from dogrcont where k_nocontrat = dos.ki_contrat)
)
group by gr.ki_grcont, gr.ka_acteur, gr.k_societe
having count(1) > 1
